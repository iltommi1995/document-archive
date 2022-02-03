import { Button } from "@material-ui/core";
import UploadFileIcon from '@mui/icons-material/UploadFile';
import axios from 'axios';
import { API_BASE_PATH } from '../../../system-variables';


export default function UploadButton(props) {

    const uploadDocument = ({ target }) => {
        let formData = new FormData();
        formData.append('document', target.files[0]);
        console.log(target.files);
        axios.post(`${API_BASE_PATH}storeDocument`, formData)
            .then(res => {
                console.log(res.data)

                if (res.data.storedOnFileSystem === "True" && res.data.storedOnDatabase === "True" && res.data.indexedOnElasticsearch === "True") {

                    axios.get(`${API_BASE_PATH}documents`)
                        .then(res => {
                            console.log(res.data)
                            props.setDocuments(res.data)
                            alert(`The document "${target.files[0].name}" has been correctly updated.`)
                        })
                        .catch(err => console.log(err));
                }
                else {
                    alert(`Error uploading file: ${res.data.error}`)
                }
            })
            .catch(err => console.log(err));
    };

    return (
        <Button variant="outlined" color="primary" startIcon={<UploadFileIcon />} component="label">
            Upload document
            <input
                type="file"
                onChange={uploadDocument}
                hidden
            />
        </Button>
    )
};