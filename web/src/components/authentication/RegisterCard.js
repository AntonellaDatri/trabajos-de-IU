import React, {useState} from "react";
import TextField from "@material-ui/core/TextField";
import {StyledAuthButton} from "../../styles/auth.styled";
import {Link, useHistory} from "react-router-dom";
import {AuthController} from "../../controllers/authController";

const RegisterCard = () => {
  const authController = new AuthController()

  const [email, setEmail] = useState('')
  const [name, setName] = useState('')
  const [password, setPassword] = useState('')
  const [image, setImage] = useState('')
  const [creditCard, setCreditCard] = useState('')
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
        authController.registerUser({
          name,
          email,
          password,
          image,
          creditCard
        }, setError, onSuccess)
      }}>
        <div><p>{error}</p></div>
        <img src="https://cdn4.iconfinder.com/data/icons/planner-color/64/popcorn-movie-time-512.png" alt="Icono de la aplicacion" className="auth-icon"/>
        <TextField type="email" label="Email" required onChange={(event) => setEmail(event.target.value)}/>
        <TextField type="text" label="Name" required onChange={(event) => setName(event.target.value)}/>
        <TextField type="password" label="Password" required onChange={(event) => setPassword(event.target.value)}/>
        <TextField type="text" label="Image link" required onChange={(event) => setImage(event.target.value)}/>
        <TextField type="text" label="Credit card" required onChange={(event) => setCreditCard(event.target.value)}/>
        <StyledAuthButton type="submit" variant="contained" color="secondary">Register</StyledAuthButton>
        <Link to='/login'>Login</Link>
      </form>
    </>
  )
}

export default RegisterCard