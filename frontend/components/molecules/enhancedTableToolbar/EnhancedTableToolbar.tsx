import { Toolbar, Typography } from "@material-ui/core";
import PropTypes from 'prop-types';
import DeleteButton from "../../atoms/deleteButton/DeleteButton";


export default function EnhancedTableToolbar(props) {
    const { numSelected } = props;

    return (
      <Toolbar>
        {numSelected > 0 ? (
          <Typography
            sx={{ flex: '1 1 100%' }}
            color="inherit"
            variant="subtitle1"
            component="div"
          >
            {numSelected} documents selected
          </Typography>
        ) : (
          <Typography
            sx={{ flex: '1 1 100%' }}
            variant="h6"
            id="tableTitle"
            component="div"
          >
            Documents
          </Typography>
        )}
  
        {numSelected > 0 ? (
              <DeleteButton 
              documentsSelected={props.documentsSelected}
              setDocumentsSelected={props.setDocumentsSelected}
              setDocuments={props.setDocuments}
              />
        ) : (
          <div></div>
        )}
      </Toolbar>
    );
  };

  EnhancedTableToolbar.propTypes = {
    numSelected: PropTypes.number.isRequired,
  };