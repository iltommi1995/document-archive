import { Card, CardContent } from '@material-ui/core';
import styles from './DocumentsContainer.module.scss';
import EnhancedTable from '../../molecules/customTable/CustomTable';
import DocumentsContainerActions from '../../molecules/documentsContainerActions/DocumentsContainerActions';

export default function DocumentsContainer(props) {
    return (
        <div className={styles.documentsContainer}>
            <Card className={styles.documentsContainerCard}>
                <DocumentsContainerActions styles={styles} setDocuments={props.setDocuments}/>
                <CardContent className={styles.documentsContainerCardContent}>
                    <EnhancedTable documents={props.documents} setDocuments={props.setDocuments}/>
                </CardContent>
            </Card>
        </div>
    )
}
