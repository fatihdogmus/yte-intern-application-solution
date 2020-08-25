import React from 'react';
import TablePagination from "@material-ui/core/TablePagination";

export default function TablePageController(props) {
  return (
    <TablePagination
      rowsPerPageOptions={[10, 25, 100]}
      component="div"
      count={props.count}
      rowsPerPage={props.rowsPerPage}
      page={props.page}
      onChangePage={props.handleChangePage}
      onChangeRowsPerPage={props.handleChangeRowsPerPage}
    />
  );
}