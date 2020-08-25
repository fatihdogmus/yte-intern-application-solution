import React, {useState} from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableContainer from '@material-ui/core/TableContainer';
import TableHeader from "./TableHeader";
import TableContent from "./TableContent";
import {TablePageController} from "./TablePageController";

export default function PaginationTable(props) {

  const [currentPage, changePage] = useState(0);
  const [rowsPerPage, changeRowsPerPage] = useState(10);


  const columns = [
    {id: 'name', label: 'Name', minWidth: 170},
    {id: 'surname', label: 'Surname', minWidth: 100},
    {id: 'email', label: 'E-Mail', minWidth: 170, align: 'right',},
    {id: 'tcKimlikNo', label: 'TC Kimlik No', minWidth: 170, align: 'right',},
    {id: 'studentNumber', label: 'Student Number', minWidth: 170, align: 'right',},
    {id: "update", label: "Update Student", align: "right", onClick: props.onUpdate},
    {id: "delete", label: "Delete Student", align: "right", onClick: props.onDelete},
    {id: "addBook", label: "Add Book", align: "right", onClick: props.onAddBook}
  ];

  const handleChangePage = (event, newPage) => {
    changePage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    changeRowsPerPage(event.target.value);
    changePage(0);
  };

  return (
    <Paper>
      <TableContainer>
        <Table stickyHeader aria-label="sticky table">
          <TableHeader columns={columns}/>
          <TableContent rows={props.rows} page={currentPage} rowsPerPage={rowsPerPage}
                        columns={columns} onAddBook={props.onAddBook}/>
        </Table>
      </TableContainer>
      <TablePageController count={props.rows.length}
                           rowsPerPage={rowsPerPage}
                           page={currentPage} handleChangePage={handleChangePage}
                           handleChangeRowsPerPage={handleChangeRowsPerPage}/>
    </Paper>
  );


}
