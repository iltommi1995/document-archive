import ListIcon from '@mui/icons-material/List';
import styles from './Header.module.scss';
import { Stack, Typography } from '@mui/material';
import NavLinkCustom from '../../atoms/navLinkCustom/NavLink';
import NavDocumentsButton from '../../atoms/navDocumentsButton/NavDocumentsButton';

export default function Header() {

    return (
        <header className={styles.header}>
            <Stack direction="row" spacing={20} className={styles.headerStack}>
                <NavLinkCustom href="/" passHref>
                   <NavDocumentsButton styles={styles} />
                </NavLinkCustom>

                <NavLinkCustom href="/lists">
                    <div className={styles.menuItem}>
                        <ListIcon fontSize="large" />
                        <Typography className={styles.menuItemtypo}>
                            LISTS
                        </Typography>
                    </div>
                </NavLinkCustom>
            </Stack>
        </header>
    )
}
