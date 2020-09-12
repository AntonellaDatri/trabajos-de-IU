import React, {useState, useEffect} from 'react';
import Api from '../controllers/ContentController'
import './SearchPage.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import Poster from '../components/content/Poster'
import NotFound from "./not-found-image.jpg";
import Navbar from '../navBar/Navbar';


function SearchPage({
  match: {
    params: { page },
  },
}) {
  const [contents, setContents] = useState([]);

  useEffect(() => {
    setContents([]);
    const authorization = sessionStorage.getItem("accessToken");

    const getImages = (id) => {
      Api.getImages(authorization, id)
        .then((response) => { verifyImage(response.data) })
        .catch(error => console.log(error.response?.data.title))
  
  
    }
  
    const verifyImage = (content) => {
      Api.verifyPoster(content.poster)
        .then((response) => {
          if (response.data !== '<h1>File not Found</h1>') {
            setContentsWithPoster(content.poster, content)
          }else{setContentsWithPoster(NotFound, content)}
        })
        .catch(error => setContentsWithPoster(NotFound, content))
    }

    Api.getContent(authorization, page)
      .then((response) => response.data.forEach((contentData) => { getImages(contentData.id) }))
      .catch(error => console.log('Error', error.response?.data.title));
  }, [page])

  const setContentsWithPoster = (p, content) => {
    setContents(prevContent =>
            prevContent.concat({
                    poster: p,
                    id: content.id,
                    title: content.title
    }))
  }

  return (
    <>
      <Navbar/>
      <div className="container-fluid" >
        <div>
          <h3 className="result"> Title related to: {page} </h3>
        </div>
        <div className="row posters">
          {contents.map(content => (
            <div key={content.id} className="poster" >
              <Poster content={content} />
            </div>
          ))}
        </div>
      </div>
    </>
  )


}


export default SearchPage;
