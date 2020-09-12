import React, { useState } from 'react';
import './Navbar.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import search from './Images/search.svg'
import userIcon from './Images/userIcon.svg'
import { Link, useHistory } from 'react-router-dom';
import Logo from './Images/unqflixLogo.png'

function Navbar (props){
    const [text, setText] = useState(props.text || "")
    const history = useHistory()

    return(
        <header >
            <nav className="navbar navbar-dark bg-dark prueba " >
                <div className="perfil">
                    <a onClick={(event) =>
                    {
                        event.preventDefault()
                        sessionStorage.removeItem('accessToken')
                        history.push("/login")
                    }}>
                        <img src={userIcon} width="40" height="40" alt="..."></img>
                    </a>
                </div>
                <div className="logo">
                    <Link  to ="/">
                        <img src={Logo} height="30" alt="..."></img>
                    </Link>
                </div>
                <form className="form-inline buttom-box"
                onSubmit={(event) => {
                    event.preventDefault()
                    history.push(`/Search/${text}`);
                  }}
                >
                    <input  className="search-input"
                            placeholder = "search"
                            onChange = { ev => setText( ev.target.value) }
                    />
                    <button className = "search-button" type="submit" >
                        <img src= {search} width="17" height="17" alt="..."></img>
                    </button>
                </form>
            </nav>
        </header>
    )

}

export default Navbar;
    