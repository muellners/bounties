import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Logout from 'app/modules/login/logout';
import Login from 'app/modules/login/login';
import Home from 'app/modules/home/home';

import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading ...</div>,
});

const Routes = () => (
  <div className="view-routes">
    <Switch>
      <ErrorBoundaryRoute path="/logout" component={Logout} />
      <ErrorBoundaryRoute path="/login" exact component={Login} />
      <ErrorBoundaryRoute path="/" exact component={Home} />

      <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />      
      <ErrorBoundaryRoute path="/" component={Entities} />
      <ErrorBoundaryRoute component={PageNotFound} />
    </Switch>
  </div>
);

export default Routes;
