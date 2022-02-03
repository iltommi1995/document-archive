
import { useState, useEffect } from 'react';
import axios from 'axios';
import { API_BASE_PATH } from '../system-variables';
import SearchBox from '../components/organisms/SearchBox/SearchBox';
import DocumentsContainer from '../components/organisms/documentsContainer/DocumentsContainer';
import Header from '../components/organisms/header/Header';

export default function Home() {
  const [documents, setDocuments] = useState([]);

  const [list, setList] = useState('');
/*
  useEffect(() => {
    (async () => {
      console.log("ok")
      axios.get(`${API_BASE_PATH}documents`)
        .then(res => {
          console.log(res.data)
          setDocuments(res.data)
        })
        .catch(err => console.log(err));
    })();
  }, []);
*/
  return (
    <div className="container">
      <Header />
      <div className='centralContent'>
        <div className='centralContentGrid'>
          <SearchBox documents={documents} setDocuments={setDocuments}/>
          <DocumentsContainer documents={documents} setDocuments={setDocuments}/>
        </div>
      </div>
    </div>
  )
}
