import React, {Component} from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableContainer from '@material-ui/core/TableContainer';
import TableHeader from "./TableHeader";
import TableContent from "./TableContent";
import {TablePageController} from "./TablePageController";

export default class PaginationTable extends Component {

  constructor(props) {
    super(props);
    this.state = {
      page: 0,
      rowsPerPage: 10,
      rows: []
    };
  }

  columns = [
    {id: 'name', label: 'Name', minWidth: 170},
    {id: 'surname', label: 'Surname', minWidth: 100},
    {id: 'email', label: 'E-Mail', minWidth: 170, align: 'right',},
    {id: 'tcKimlikNo', label: 'TC Kimlik No', minWidth: 170, align: 'right',},
    {id: 'studentNumber', label: 'Student Number', minWidth: 170, align: 'right',},
    {id: "update", label: "Update Student", align: "right", onClick: this.props.onUpdate},
    {id: "delete", label: "Delete Student", align: "right", onClick: this.props.onDelete},
    {id: "addBook", label: "Add Book", align: "right", onClick: this.props.onAddBook}
  ];

  handleChangePage = (event, newPage) => {
    this.setState({page: newPage});
  };

  handleChangeRowsPerPage = (event) => {
    this.setState({
      rowsPerPage: event.target.value,
      page: 0
    });
  };


  render() {
    return (
      <Paper>
        <TableContainer>
          <Table stickyHeader aria-label="sticky table">
            <TableHeader columns={this.columns}/>
            <TableContent rows={this.props.rows} page={this.state.page} rowsPerPage={this.state.rowsPerPage}
                          columns={this.columns} onAddBook={this.props.onAddBook}/>
          </Table>
        </TableContainer>
        <TablePageController count={this.state.rows.length}
                             rowsPerPage={this.state.rowsPerPage}
                             page={this.state.page} handleChangePage={this.handleChangePage}
                             handleChangeRowsPerPage={this.handleChangeRowsPerPage}/>
      </Paper>
    );
  }


}
