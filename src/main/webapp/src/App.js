import React, {useEffect, useState} from 'react';
import PaginationTable from "./components/table/PaginationTable";
import Button from "@material-ui/core/Button";
import PlusIcon from '@material-ui/icons/Add';
import axios from "axios";
import ReactDialog from "./components/common/ReactDialog";
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function App(props) {

  const studentDialogFields = [
    {id: "name", label: "Name", type: "text"},
    {id: "surname", label: "Surname", type: "text"},
    {id: "email", label: "E-Mail", type: "email"},
    {id: "tcKimlikNo", label: "TC Kimlik No", type: "text"},
    {id: "studentNumber", label: "Student Number", type: "text"},
  ]

  const [rows, updateRows] = useState([]);
  const [isAddStudentModalOpen, updateIsAddStudentModalOpen] = useState(false);

  const toastOptions = {
    position: "top-right",
    autoClose: 5000,
    hideProgressBar: true,
    closeOnClick: true,
    pauseOnHover: false,
    draggable: false,
    progress: undefined,
  };

  useEffect(() => {
    axios.get("/students")
      .then(response => {
        updateRows(response.data)
      })
  }, [])


  const toggleAddStudentModal = () => {
    updateIsAddStudentModalOpen(!isAddStudentModalOpen);
  }

  const submitStudentAdd = (inputData) => {
    toggleAddStudentModal();
    axios.post("/students", inputData)
      .then(response => {
        console.log(response.data);
        if (response.data.messageType === "SUCCESS") {
          toast.success(response.data.message, toastOptions);
          updateRows([...rows, inputData]);
        } else {
          toast.error(response.data.message, toastOptions);
        }
      });
  }

  const onStudentDelete = (studentNumber) => {
    axios.delete("/students/" + studentNumber)
      .then(response => {
        if (response.data.messageType === "SUCCESS") {
          updateRows(rows.filter((student) => student.studentNumber !== studentNumber));
          toast.success(response.data.message, toastOptions);
        } else {
          toast.error(response.data.message, toastOptions);
        }
      })
  }

  const onAddBook = (inputData) => {
    console.log(inputData);
    //Burada Book eklemek için bir modal açıp student'a benzer bir mantık yapılmalı, fakat burayı yapmak için vaktim olmadı
  }

  const studentTableColumns = [
    {id: 'name', label: 'Name', minWidth: 170},
    {id: 'surname', label: 'Surname', minWidth: 100},
    {id: 'email', label: 'E-Mail', minWidth: 170, align: 'right',},
    {id: 'tcKimlikNo', label: 'TC Kimlik No', minWidth: 170, align: 'right',},
    {id: 'studentNumber', label: 'Student Number', minWidth: 170, align: 'right',},
    {id: "update", label: "Update Student", align: "right", onClick: onStudentDelete},
    {id: "delete", label: "Delete Student", align: "right", onClick: onStudentDelete},
    {id: "addBook", label: "Add Book", align: "right", onClick: onAddBook}
  ];

  return (
    <div className="App">
      <Button variant="contained"
              color="primary"
              style={{float: "right"}}
              onClick={toggleAddStudentModal}
              startIcon={<PlusIcon/>}>
        Add student
      </Button>
      <ReactDialog fields={studentDialogFields} title="Add Student" isOpen={isAddStudentModalOpen}
                   onClose={toggleAddStudentModal}
                   onSubmit={submitStudentAdd}/>
      <PaginationTable rows={rows} columns={studentTableColumns}/>
      <ToastContainer/>
    </div>
  );

}
