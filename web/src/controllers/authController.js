const axios = require('axios')

export class AuthController{

  registerUser(user, onError, onSuccess){
    axios.post('http://localhost:7000/register', {
      ...user
    }).then((response) => {
      sessionStorage.setItem('accessToken', response.headers.authorization)
      onSuccess()
    }).catch((error) => {
      if(error.response && error.response.data.message === 'The email is already associated with a user'){
        onError('El email ya esta registrado')
      } else{
        onError('Lo sentimos, ha ocurrido un error.')
      }
    })
  }

  login(user, onError, onSuccess) {
    axios.post('http://localhost:7000/login', {
      ...user
    }).then((response) => {
      sessionStorage.setItem('accessToken', response.headers.authorization)
      onSuccess()
    }).catch((error) => {
      if(error.response && error.response.data.message === 'User not found'){
        onError('Usuario o password incorrecto')
      } else{
        onError('Lo sentimos, ha ocurrido un error.')
      }
    })
  }
}