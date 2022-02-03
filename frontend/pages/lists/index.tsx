
import Header from '../../components/organisms/header/Header';

export default function Lists() {

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
        <h1>Lists</h1>
      </div>
    </div>
  )
}
