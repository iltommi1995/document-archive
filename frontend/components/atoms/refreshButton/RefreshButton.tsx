import { Button } from "@material-ui/core";
import RefreshIcon from '@mui/icons-material/Refresh';
import axios from 'axios';
import { API_BASE_PATH } from '../../../system-variables';

export default function RefreshButton(props) {

    const refreshFolder = () => {
        axios.get(`${API_BASE_PATH}processDirectory`)
            .then(res => {
                axios.get(`${API_BASE_PATH}documents`)
                    .then(res2 => {
                        console.log(res2.data)
                        props.setDocuments(res2.data)
                    })
                    .catch(err => console.log(err));
            })
            .catch(err => console.log(err));
    };

    return (
        <Button variant="outlined" startIcon={<RefreshIcon />} onClick={refreshFolder}>
            Force folder refresh
        </Button>
    )
};