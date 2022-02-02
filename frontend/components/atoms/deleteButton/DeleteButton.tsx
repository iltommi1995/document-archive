import { useState } from 'react';
import { Box, Button, IconButton, Modal, Typography } from "@material-ui/core";
import DeleteIcon from '@mui/icons-material/Delete';
import axios from 'axios';
import { API_BASE_PATH } from '../../../system-variables';
import { Stack } from '@mui/material';

export default function DeleteButton(props) {
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => { setOpen(false); console.log("ok") };

    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    };

    const deleteDocument = () => {
        props.documentsSelected.map(document => {
            axios.delete(`${API_BASE_PATH}deleteDocument?documentId=${document}`)
                .then(res => {
                    console.log(res.data)
                    props.setDocumentsSelected([]);

                    axios.get(`${API_BASE_PATH}documents`)
                        .then(res => {
                            console.log(res.data)
                            props.setDocuments(res.data)

                            handleClose;
                        })
                        .catch(err => console.log(err));
                })
                .catch(err => console.log(err));
        })

        if (props.documentsSelected.length > 1)
            alert("Documents correctly deleted.")
        else
            alert("Document correctly deleted.")
    }

    return (
        <div>
            <IconButton onClick={handleOpen}>
                <DeleteIcon />
            </IconButton>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                        Delete documents
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                        Are you sure you want to delete documents:
                    </Typography>
                    <ul>
                        {props.documentsSelected.map(document => {
                            return (
                                <li key={`KW-${document}`}>{document}</li>
                            )
                        })}
                    </ul>
                    <Stack direction="row" spacing={"60%"}>
                        <Button variant="contained" color="primary" onClick={deleteDocument}>
                            Yes
                        </Button>
                        <Button variant="outlined" color="secondary" onClick={handleClose}>
                            No
                        </Button>
                    </Stack>
                </Box>
            </Modal>
        </div>
    )
};