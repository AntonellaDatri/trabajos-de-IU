import Button from "@material-ui/core/Button";
import {withStyles} from "@material-ui/core";

export const StyledAuthButton = withStyles({
    root: {
      marginTop: '1vh',
      fontSize: '1.5em'
    }
  })(Button)