import { Typography } from '@mui/material'
import FindInPageIcon from '@mui/icons-material/FindInPage';
import React from 'react';

const NavDocumentsButton = React.forwardRef(({ onClick, href, styles}, ref) =>  {
    return (
        <a href={href} ref={ref} onClick={onClick} className={styles.menuItem}>
            <FindInPageIcon fontSize="large" />
            <Typography className={styles.menuItemtypo}>
                DOCUMENTS
            </Typography>
        </a>
    )
})

export default NavDocumentsButton;
