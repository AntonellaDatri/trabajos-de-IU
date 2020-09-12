import React, {useState, useEffect} from 'react';
import Api from '../controllers/ContentController'
import 'bootstrap/dist/css/bootstrap.min.css'
import Carousel from './Carousel';
import Navbar from '../navBar/Navbar';
import MediaCarrousel from '../mediaCarrousel/MediaCarrousel'
import AllContent from './AllContent'
import './home.css'

 
 
function Home() {
    const [favorites, setFavorites] = useState([])
    const [lastSeen, setLastSeen] = useState([])
    const [showContent, setShowContent] = useState(false)
    const [text, setText] = useState('Show All Content')
    const authorization = sessionStorage.getItem("accessToken")
 
    useEffect(() => {
        Api.getUser(authorization)
            .then(response => {
                setFavorites(response.data.favorites)
                setLastSeen(response.data.lastSeen)
            })
            .catch(error => console.log('Error', error.response?.data.title));
    }, [])

    const viewContent = (() => {
        setShowContent(!showContent)
        if (text === 'Show All Content'){
            setText('Close content')
        } else {setText('Show All Content')}
        
    })

    return (
      <>
        <Navbar/>
        <div > 
            <Carousel />
           <div className = "container-fluid "> 
                <div className="items-style"> 
                    <h4>Favorites</h4>
                    <MediaCarrousel content = {favorites}/> 
                </div>
                <div className="items-style"> 
                    <h4>Last Seen</h4>
                    <MediaCarrousel content = {lastSeen} /> 
                </div>
                <div className= "button-position"> 
                    <button type="button" className="btn btn-danger viewContentButton" onClick={viewContent}>{text}</button>
                </div>
            </div>
        </div>
        {showContent && <AllContent/>}
      </>
    )
}
 
export default Home;
