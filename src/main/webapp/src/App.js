import React, {Component} from 'react';
import PaginationTable from "./components/table/PaginationTable";
import Button from "@material-ui/core/Button";
import PlusIcon from '@material-ui/icons/Add';
import axios from "axios";
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";
import ReactDialog from "./components/common/ReactDialog";

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
      snackbarProperties: {
        isOpen: false,
        message: "",
        severity: ""
      }
    }
  }

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
        this.setState(prevState => (
          {rows: [...prevState.rows, inputData]}
          ));
        this.snackbarOpen("Student has been added successfully!", "success");
      })
      .catch(error => {
        if (error.response.status === 400) {
          this.snackbarOpen(error.response.data.errors[0].defaultMessage, "error")
        }
        console.log(error.response);
      })
  }

  snackbarOpen = (message, severity) => {
    console.log(message, severity);
    this.setState(prevState => {
      let snackbarProperties = {...prevState.snackbarProperties}
      snackbarProperties.isOpen = true;
      snackbarProperties.message = message;
      snackbarProperties.severity = severity;
      return {snackbarProperties};
    })
  }

  snackbarClose = () => {
    this.setState(prevState => {
      let snackbarProperties = {...prevState.snackbarProperties}
      snackbarProperties.isOpen = false;
      snackbarProperties.message = "";
      snackbarProperties.severity = "";
      return {snackbarProperties};
    })
  }

  onStudentDelete = (studentNumber) => {
    axios.delete("/students/" + studentNumber)
      .then(response => {
        this.setState( {
          rows: this.state.rows.filter((student) => student.studentNumber !== studentNumber)
        })
        this.snackbarOpen("Student with student number " + studentNumber + "has been deleted!", "success")
      })
  }

  onAddBook = (inputData) => {
    axios.post("/students/" + inputData.studentNumber + "/books", inputData)
      .then(response => {
        this.snackbarOpen("Book has been added successfully!", "success");
      })
      .catch(error => {
        if (error.response.status === 400) {
          this.snackbarOpen(error.response.data.errors[0].defaultMessage, "error")
        }
        console.log(error.response);
      })
  }

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
        <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                  anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
          <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
            {this.state.snackbarProperties.message}
          </Alert>
        </Snackbar>
        <ReactDialog fields={this.studentDialogFields} title="Add Student" isOpen={this.state.addStudentModalOpen} onClose={this.toggleAddStudentModal}
                         onSubmit={this.submitStudentAdd}/>
        <PaginationTable rows={this.state.rows} onUpdate={this.onStudentDelete} onDelete={this.onStudentDelete} onAddBook={this.onAddBook}/>
      </div>
    );
  }


}

export default App;
