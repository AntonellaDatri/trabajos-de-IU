import React, {useState, useEffect} from 'react';
import Api from '../controllers/ContentController'
import Poster from '../components/content/Poster'
import NotFound from "../search/not-found-image.jpg";
import "../search/SearchPage.css";
 
function AllContent ({content}){ 
    const [contents, setContents] = useState([]);
    const authorization = sessionStorage.getItem("accessToken")

  useEffect(() => {
    Api.getAllContent(authorization)
      .then((response) => response.data.forEach((contentData) => { getImages(contentData.id) }))
      .catch(error => console.log('Error', error.response?.data.title));
  }, [])

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
        <div className="container-fluid" >
            <div className="row posters">
            {contents.map(content => (
                <div key={content.id} className="poster" >
                  <Poster content={content} height='250' />
                </div>
            ))}
            </div>
        </div>    
    </>
  )


}
 
export default AllContent