import SearchBar from '../../molecules/searchBar/SearchBar';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { API_BASE_PATH } from '../../../system-variables';

export default function SearchBox(props) {
    const [keywords, setKeywords] = useState([]);
    const [searchedKeyword, setSearchedKeyword] = useState('');

    const removeKeyword = (kw) => {
        let kws = [];
        keywords.forEach(k => {
            if(k != kw)
                kws.push(k);
        })
        setKeywords(kws);

        if(keywords.length === 0) {
            axios.get(`${API_BASE_PATH}documents`)
            .then(res => {
                console.log("passo da qui")
                props.setDocuments(res.data)
            })
            .catch(err => console.log(err));
        }
    }

    return (
        <div className='searchBox'>
            <SearchBar 
                keywords={keywords}
                setKeywords={setKeywords}
                searchedKeyword={searchedKeyword}
                setSearchedKeyword={setSearchedKeyword}
                documents={props.documents} 
                setDocuments={props.setDocuments}
            />
            <div>

                <ul>
                    {keywords.map(kw => {
                        return(
                            <li key={`kw-${kw}`} >{kw} <span onClick={() => removeKeyword(kw)}>X</span></li>
                        );
                    })}
                </ul>
            </div>
        </div>
    )
}
