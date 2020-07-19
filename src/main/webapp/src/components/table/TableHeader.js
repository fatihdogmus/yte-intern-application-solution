import React from 'react';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";

export default function TableHeader(props) {

  return (
    <TableHead>
      <TableRow>
        {props.columns.map((column) => createTableCell(column))}
      </TableRow>
    </TableHead>
  );

  function createTableCell(column) {
    return <TableCell
      key={column.id}
      align={column.align}
      style={{minWidth: column.minWidth}}>
      {column.label}
    </TableCell>;
  }
}
