import React, {useState} from "react";
import TextField from "@material-ui/core/TextField";
import {StyledAuthButton} from "../../styles/auth.styled";
import {Link, withRouter} from "react-router-dom";
import {AuthController} from "../../controllers/authController";
import { useHistory } from "react-router-dom";

const LoginCard = () => {
  const authController = new AuthController();
  const [email, setMail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const history = useHistory()

  const onSuccess = () => {
    history.push("/")
  }

  return (
    <>
      <div className="left-decoration"/>
      <form className="auth-form" onSubmit={(event) => {
        event.preventDefault()
        authController.login({
          email,
          password
        },setError, onSuccess)
      }}>
        <div><p>{error}</p></div>
        <img src="https://cdn4.iconfinder.com/data/icons/planner-color/64/popcorn-movie-time-512.png" alt="Icono de la aplicacion" className="auth-icon"/>
        <TextField type="text" label="User" onChange={(event) => setMail(event.target.value)}/>
        <TextField type="password" label="Password" onChange={(event) => setPassword(event.target.value)}/>
        <StyledAuthButton variant="contained" color="secondary" type="submit">Login</StyledAuthButton>
        <Link to='/register'>Register</Link>
      </form>
    </>
  )
}

export default withRouter(LoginCard)