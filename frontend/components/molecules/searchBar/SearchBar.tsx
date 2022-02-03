import { useEffect } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import { IconButton, InputBase, Paper } from '@material-ui/core';
import axios from 'axios';
import { API_BASE_PATH } from '../../../system-variables';
import styles from './SearchBar.module.scss';

export default function SearchBar(props) {

    const searchKeyword = () => {
        console.log(props.searchedKeyword);
        let kw = [];
        props.keywords.forEach(key => kw.push(key));
        kw.push(props.searchedKeyword);
        props.setKeywords(kw);
        props.setSearchedKeyword('');
    }

    useEffect(() => {
        if(props.keywords.length > 0) {
            console.log(props.keywords);
            let queryParams = "";
            props.keywords.forEach(kw => {
                queryParams += `skill=${kw}&`;
            })

            queryParams = queryParams.substring(0, queryParams.length - 1);
    
            axios.get(`${API_BASE_PATH}search?${queryParams}`)
                            .then(res => {
                                props.setDocuments(res.data)
                            })
                            .catch(err => console.log(err));
        }
        else {
                axios.get(`${API_BASE_PATH}documents`)
                .then(res => {
                    props.setDocuments(res.data)
                })
                .catch(err => console.log(err));
        }
     }, [props.keywords]);

    return (
        <Paper component="form" className={styles.searchBar}>
            <InputBase
                placeholder="Search for keyword"
                inputProps={{ 'aria-label': 'search for keyword' }}
                value={props.searchedKeyword}
                onChange={(event) => {props.setSearchedKeyword(event.target.value)}}
            />
            <IconButton  aria-label="search" onClick={searchKeyword}>
                <SearchIcon />
            </IconButton>
        </Paper>
    )
}
