
import { useState, useEffect } from 'react';
import axios from 'axios';
import ListIcon from '@mui/icons-material/List';
import FindInPageIcon from '@mui/icons-material/FindInPage';
import Link from 'next/link'
import styles from './Header.module.scss';
import { Button, Stack, Typography } from '@mui/material';

export default function Header() {

    return (
        <header className={styles.header}>
            <Stack direction="row" spacing={20} className={styles.headerStack}>
                <Link href="/">
                    <div className={styles.menuItem}>

                        <FindInPageIcon fontSize="large" />
                        <Typography className={styles.menuItemtypo}>
                            DOCUMENTS
                        </Typography>
                    </div>

                </Link>

                <Link href="/lists">

                    <div className={styles.menuItem}>

                        <ListIcon fontSize="large" />
                        <Typography className={styles.menuItemtypo}>
                            LISTS
                        </Typography>
                    </div>
                </Link>
            </Stack>
        </header>
    )
}
