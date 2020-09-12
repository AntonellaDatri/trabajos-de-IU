import axios from 'axios'
 
const getAllBanners = (authorization) => axios.get('http://localHost:7000/banners', { headers : { Authorization : authorization} })

const getAllContent = (authorization, search) => axios.get(`http://localHost:7000/content`, { headers : { Authorization : authorization} })

const getContent = (authorization, search) => axios.get(`http://localHost:7000/search?text=${search}`, { headers : { Authorization : authorization} })
 
const getImages = (authorization, id) => axios.get(`http://localHost:7000/content/${id}`, { headers : { Authorization : authorization} })
 
const verifyPoster = (poster) => axios.get(poster)
 
const getUser = (authorization) => axios.get('http://localHost:7000/user', { headers : { Authorization : authorization} } )

const addLastSeen = (authorization, lastSeenId) => axios.post('http://localhost:7000/user/lastSeen', { id : lastSeenId }, { headers : { Authorization : authorization} })
 
const addFavorite = (authorization, id) => axios.post(`http://localHost:7000/user/fav/${id}`,{} ,{ headers : { Authorization : authorization} } )
                    .then (response => console.log('Error', response.data))
                    .catch(error => console.log('Error', error.response?.data.title))
 
export default {
    getAllBanners,
    getAllContent,
    getContent,
    getImages,
    verifyPoster,
    getUser,
    addLastSeen,
    addFavorite
};
