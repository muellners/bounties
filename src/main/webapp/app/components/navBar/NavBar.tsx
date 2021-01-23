import React from 'react';
import { makeStyles } from '@material-ui/core/styles';

import AppBar from '@material-ui/core/AppBar';
import Box from '@material-ui/core/Box';
import Toolbar from '@material-ui/core/Toolbar';
import Link from '@material-ui/core/Link';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';

import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';

import LayersIcon from '@material-ui/icons/Layers';
import FilterHdrIcon from '@material-ui/icons/FilterHdr';
import DirectionsBusIcon from '@material-ui/icons/DirectionsBus';
import NotificationImportantIcon from '@material-ui/icons/NotificationImportant';
import { getLoginUrl } from 'app/shared/util/url-utils';

const useStyles = makeStyles((theme) => ({
  toolbar: {
    minHeight: 70
  },
  brand: {
    lineHeight: 1,
    marginRight: 'auto'
  },
  link: {
    marginRight: theme.spacing(5),
    [theme.breakpoints.down('sm')]: {
      display: 'none'
    }
  },
  linkBrand: {
    flexGrow: 1,
    [theme.breakpoints.down('xs')]: {
      display: 'none',
    }
  },
  primaryAction: {
    [theme.breakpoints.down('sm')]: {
      display: 'none'
    }
  },
  menuButton: {
    [theme.breakpoints.up('md')]: {
      display: 'none'
    }
  },
  iconWrapper: {
    minWidth: 40,
  },
  icon: {
    color: theme.palette.text.hint
  },
  drawerContainer: {
    paddingTop: theme.spacing(2),
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(3),
    width: 300,
  }
}));

export default function NavBar(props) {
  const classes = useStyles();

  const content = {
    'brand': { image: 'content/images/nereus-light.png', width: 110 },
    'link1': 'Home',
    'link2': 'Section Two',
    'link3': 'Section Three',
    'link4': 'Section Four',
    'primary-action': 'Sign in',
    ...props.content
  };

  let brand;

  if (content.brand.image) {
    brand = <img src={ content.brand.image } alt="" width={ content.brand.width } />;
  } else {
    brand = content.brand.text || '';
  }

  const [state, setState] = React.useState({ open: false });

  const toggleDrawer = (open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }

    setState({ ...state, open });
  };

  return (
    <AppBar position="static" color="inherit">
      <Toolbar className={classes.toolbar}>
        <Link href="#" color="primary" underline="none" variant="h5" className={classes.brand}>
          {brand}
        </Link>
        <Link href="#" color="textPrimary" variant="body2" className={classes.link}>
          {content['link1']}
        </Link>
        <Link href="#" color="textPrimary" variant="body2" className={classes.link}>
          {content['link2']}
        </Link>
        <Link href="#" color="textPrimary" variant="body2" className={classes.link}>
          {content['link3']}
        </Link>
        <Link href="#" color="textPrimary" variant="body2" className={classes.link}>
          {content['link4']}
        </Link>
        <Button
          variant="contained"
          color="secondary"
          href={getLoginUrl()}
          className={classes.primaryAction}
        >
          {content['primary-action']}
        </Button>
        <IconButton edge="start" color="inherit" aria-label="menu" className={classes.menuButton} onClick={toggleDrawer(true)}>
          <MenuIcon />
        </IconButton>
      </Toolbar>
      
    </AppBar>
  );
}