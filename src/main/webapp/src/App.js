import React, {Component} from 'react';
import PaginationTable from "./components/table/PaginationTable";
import Button from "@material-ui/core/Button";
import PlusIcon from '@material-ui/icons/Add';
import axios from "axios";
import ReactDialog from "./components/common/ReactDialog";
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class App extends Component {

  studentDialogFields = [
    {id: "name", label: "Name", type: "text"},
    {id: "surname", label: "Surname", type: "text"},
    {id: "email", label: "E-Mail", type: "email"},
    {id: "tcKimlikNo", label: "TC Kimlik No", type: "text"},
    {id: "studentNumber", label: "Student Number", type: "text"},
  ]

  constructor() {
    super();
    this.state = {
      rows: [],
      addStudentModalOpen: false,
    }
  }

  toastOptions = {
    position: "top-right",
    autoClose: 5000,
    hideProgressBar: true,
    closeOnClick: true,
    pauseOnHover: false,
    draggable: false,
    progress: undefined,
  };

  componentDidMount() {
    axios.get("/students")
      .then(response => {
        this.setState({rows: response.data})
      })
  }

  toggleAddStudentModal = () => {
    this.setState({addStudentModalOpen: !this.state.addStudentModalOpen})
  }

  submitStudentAdd = (inputData) => {
    this.toggleAddStudentModal();
    axios.post("/students", inputData)
      .then(response => {
        console.log(response.data);
        if (response.data.messageType === "SUCCESS") {
          toast.success(response.data.message, this.toastOptions);
          this.setState(prevState => ({rows: [...prevState.rows, inputData]}));
        } else {
          toast.error(response.data.message, this.toastOptions);
        }
      });
  }

  onStudentDelete = (studentNumber) => {
    axios.delete("/students/" + studentNumber)
      .then(response => {
        if (response.data.messageType === "SUCCESS") {
          this.setState({
            rows: this.state.rows.filter((student) => student.studentNumber !== studentNumber)
          })
          toast.success(response.data.message, this.toastOptions);
        } else {
          toast.error(response.data.message, this.toastOptions);
        }
      })
  }

  onAddBook = (inputData) => {
    console.log(inputData);
    //Burada
  }

  studentTableColumns = [
    {id: 'name', label: 'Name', minWidth: 170},
    {id: 'surname', label: 'Surname', minWidth: 100},
    {id: 'email', label: 'E-Mail', minWidth: 170, align: 'right',},
    {id: 'tcKimlikNo', label: 'TC Kimlik No', minWidth: 170, align: 'right',},
    {id: 'studentNumber', label: 'Student Number', minWidth: 170, align: 'right',},
    {id: "update", label: "Update Student", align: "right", onClick: this.onStudentDelete},
    {id: "delete", label: "Delete Student", align: "right", onClick: this.onStudentDelete},
    {id: "addBook", label: "Add Book", align: "right", onClick: this.onAddBook}
  ];

  render() {

    return (
      <div className="App">
        <Button variant="contained"
                color="primary"
                style={{float: "right"}}
                onClick={this.toggleAddStudentModal}
                startIcon={<PlusIcon/>}>
          Add student
        </Button>
        <ReactDialog fields={this.studentDialogFields} title="Add Student" isOpen={this.state.addStudentModalOpen}
                     onClose={this.toggleAddStudentModal}
                     onSubmit={this.submitStudentAdd}/>
        <PaginationTable rows={this.state.rows} columns={this.studentTableColumns}/>
        <ToastContainer/>
      </div>
    );
  }


}

export default App;
