import React, {useState} from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import TextField from "@material-ui/core/TextField";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";

export default function ReactDialog(props) {

  const [inputData, updateInputData] = useState({});

  const handleInputChange = (event) => {
    event.persist();
    let newInputData = {...inputData};
    newInputData[event.target.id] = event.target.value;
    updateInputData(newInputData);
  }

  return (
    <Dialog open={props.isOpen} onClose={props.onClose} aria-labelledby="form-dialog-title">
      <DialogTitle id="form-dialog-title">{props.title}</DialogTitle>
      <DialogContent>
        {props.fields.map(field => (
            <TextField
              margin="dense" id={field.id} key={field.id}
              label={field.label} type={field.type} fullWidth
              onChange={handleInputChange}/>
          )
        )}

      </DialogContent>
      <DialogActions>
        <Button onClick={props.onClose} color="primary">
          Cancel
        </Button>
        <Button onClick={() => {
          props.onSubmit(inputData)
        }} color="primary">
          Submit
        </Button>
      </DialogActions>
    </Dialog>
  );

}