import React, { useState, useEffect } from "react";
import Api from "../../../controllers/ContentController";
import "../../../styles/details.css";

const AddFavorite = ({contentID}) => {
const [favoriteText, setFavoriteText] = useState("")
const authorization = sessionStorage.getItem("accessToken")

useEffect(() => {
    Api.getUser(authorization)
        .then(response => {
            if (response.data.favorites.some(isContent)){
                setFavoriteText("Delete favorites")
            }else{setFavoriteText("Add to favorites")}
        })
        .catch(error => console.log('Error', error.response?.data.title));
}, [])

const isContent = (content) => content.id === contentID

const addFavorite = () => {
    Api.addFavorite(sessionStorage.getItem("accessToken"), contentID)
    if(favoriteText === "Add to favorites"){
        setFavoriteText("Delete favorites")
    }else {setFavoriteText("Add to favorites")}
    
    }
    
    return(
        <button type="button" className="btn btn-danger addFavoriteBotton" onClick={addFavorite} >{favoriteText}</button>
    )
}
export default AddFavorite;