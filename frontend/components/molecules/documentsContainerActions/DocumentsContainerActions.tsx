import { CardActions } from "@material-ui/core";
import RefreshButton from "../../atoms/refreshButton/RefreshButton";
import UploadButton from "../../atoms/uploadButton/UploadButton";

export default function DocumentsContainerActions(props) {
    return (
        <CardActions className={props.styles.documentsContainerCardActions}>
            <UploadButton setDocuments={props.setDocuments} />
            <RefreshButton setDocuments={props.setDocuments} />
        </CardActions>
    )
};