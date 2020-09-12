import React, {useState, useEffect} from 'react';
import './home.css';
import Poster from '../components/content/Poster'
import Api from '../controllers/ContentController'
import ContentController from '../controllers/ContentController';
 
    
function Carousel (props) {
    const [contents, setContents] = useState([]);
    const authorization = sessionStorage.getItem("accessToken")
    
    useEffect(() => {
        Api.getAllBanners(authorization)
            .then(response => (response.data.forEach((contentData) => {getImages(contentData.id)} )))
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
          setContents(prevContent =>
            prevContent.concat({
            poster: content.poster,
            id: content.id,
            title: content.title
          }))
        }
      })
      .catch(error => console.log('La imagen no Existe'))
  }
 
    const verify = (content) => {
        if (content){ 
            return (
                <div className="banner-style">
                    <Poster content={content} alt={content.title}/>
                </div>
            )
        }
    }
 
   
    let [fst, ...rest] = contents;         
    return(
        <div id="carouselExampleControls" className="carousel slide" data-ride="carousel">
            <div className="carousel-inner">
                <div className="carousel-item active">
                    {verify(fst)}
                </div> 
                {rest.map (content => (
                    <div key={content.id} className="carousel-item ">
                        {verify(content)}
                    </div> 
                ))}
            
            </div>
            <a className="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="sr-only">Previous</span>
            </a>
            <a className="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="sr-only">Next</span>
            </a>
        </div>
    );
     
};
 
 
 
 
export default Carousel;
