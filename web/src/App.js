import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { ThemeProvider } from "@material-ui/core/styles";
import "./App.css";
import Login from "./components/pages/Login";
import theme from "./styles/theme";
import Register from "./components/pages/Register";
import SearchPage from "./search/SearchPage";
import Home from "./home/Home";
import { PrivateRoute } from "./PrivateRoute";
import Details from "./components/pages/details/Details";
import DetailsNotFound from "./components/pages/details/DetailsNotFound";
import NotFoundPage from "./components/pages/NotFoundPage";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Router>
        <Switch>
          <Route path="/login">
            <Login />
          </Route>
          <Route path="/register">
            <Register />
          </Route>
          <PrivateRoute path="/Search/:page" component={SearchPage} />
          <PrivateRoute path="/details/:id" component={Details} />
          <PrivateRoute path="/detailsNotFound" component={DetailsNotFound} />
          <PrivateRoute exact path="/" component={Home} />
          <PrivateRoute path="*" component={NotFoundPage} />
        </Switch>
      </Router>
    </ThemeProvider>
  );
}

export default App;
