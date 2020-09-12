import React from "react";
import { Typography } from "@material-ui/core";
import NotFound from "../../../search/not-found-image.jpg";
import Navbar from "../../../navBar/Navbar";

const DetailsNotFound = () => {
  return (
    <>
      <Navbar />
      <img height="400px" width="300px" alt="NotFound" src={NotFound} style={{display:'block', margin:'auto'}}/>
      <Typography variant="h2">{`El contenido no se encuentra disponible`}</Typography>
    </>
  );
};

export default DetailsNotFound;
