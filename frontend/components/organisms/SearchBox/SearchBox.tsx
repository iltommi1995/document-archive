import SearchBar from '../../molecules/searchBar/SearchBar';
import { useState } from 'react';
import axios from 'axios';
import { API_BASE_PATH } from '../../../system-variables';
import { Card, IconButton, Typography } from '@mui/material';
import styles from './SearchBox.module.scss';
import CancelIcon from '@mui/icons-material/Cancel';

export default function SearchBox(props) {
    const [keywords, setKeywords] = useState([]);
    const [searchedKeyword, setSearchedKeyword] = useState('');

    const removeKeyword = (kw) => {
        let kws = [];
        keywords.forEach(k => {
            if (k != kw)
                kws.push(k);
        })
        setKeywords(kws);

        if (keywords.length === 0) {
            axios.get(`${API_BASE_PATH}documents`)
                .then(res => {
                    props.setDocuments(res.data)
                })
                .catch(err => console.log(err));
        }
    }

    return (
        <div className={styles.searchBox}>
            <SearchBar
                keywords={keywords}
                setKeywords={setKeywords}
                searchedKeyword={searchedKeyword}
                setSearchedKeyword={setSearchedKeyword}
                documents={props.documents}
                setDocuments={props.setDocuments}
            />
            <Typography variant="h6" component="h4">
                Searched keywords:
            </Typography>
            <div className={styles.keywordsContainer}>
                {keywords.length === 0 ?
                    (
                        <Typography >
                            No keywords selected.
                        </Typography>
                    )
                    :
                    (
                        keywords.map(kw => {
                            return (
                                <Card key={`kw-${kw}`} variant="outlined" className={styles.customCard} >
                                    <Typography >
                                        {kw}
                                    </Typography>
                                    <IconButton color="secondary" aria-label="upload picture" component="span" onClick={() => { removeKeyword(kw) }}>
                                        <CancelIcon />
                                    </IconButton>

                                </Card>
                            );
                        })
                    )}
            </div>
        </div>
    )
}
