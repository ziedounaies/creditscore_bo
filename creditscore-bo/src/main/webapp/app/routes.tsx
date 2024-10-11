import React from 'react';
import {Route, Routes} from 'react-router-dom';
import Loadable from 'react-loadable';
import ForgetPassword from '../app/modules/resetPassword/resetPassword';
import LoginRedirect from 'app/modules/login/login-redirect';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import EntitiesRoutes from 'app/entities/routes';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import { Flex, Spin } from 'antd';
import MemberUserRoutes from "app/modules/dashboard/member-user";

const loading = (
  <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
    <Flex align="center" gap="middle">
      <Spin size="small" />
      <Spin />
      <Spin size="large" />
    </Flex>
  </div>
);

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => loading,
});

const Dashboard = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/dashboard'),
  loading: () => loading,
});

const AppRoutes = () => {
  return (
    <div className="view-routes">
      <ErrorBoundaryRoutes>

        <Route index element={<Home />} />
        <Route path="logout" element={<Logout />} />
         <Route path="forgetPassword" element={<ForgetPassword />} />
        <Route
          path="admin/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.MERCHANT]}>
              <Admin />
            </PrivateRoute>
          }
        />


        <Route
          path="dashboard/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN,AUTHORITIES.MERCHANT,AUTHORITIES.USER]}>
              <Dashboard />
            </PrivateRoute>
          }
        />





        <Route path="oauth2/authorization/oidc" element={<LoginRedirect />} />
        <Route
          path="*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>

               <EntitiesRoutes />
            </PrivateRoute>
          }
        />
        <Route path="*" element={<PageNotFound />} />
      </ErrorBoundaryRoutes>
    </div>
  );
};

export default AppRoutes;
