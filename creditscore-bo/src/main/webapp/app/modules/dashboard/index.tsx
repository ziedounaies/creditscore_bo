import React from 'react';
import Overview from '../dashboard/overview/overview'
import Chercher from '../dashboard/chercher/chercher'
import { Route } from 'react-router-dom';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import MemberUserRoutes from './member-user';
import MemberUserCommercantRoutes from './member-user-commercant';
import Address from "./address";
import Agencies from "./agencies"
import Claim from "./claim"
import ClaimUser from "./claim-user"
import Contact from'./contact'
import CreditRapport from './credit-rapport'
import Notification from './notification'
import Payment from './payment'
import Product from './product'
import ResetPasswordUser from './resetPasswordUser/resetPasswordUser'
import DashboardLayout from "app/modules/dashboard/layout";
import AchatProduitProcessus from "app/modules/dashboard/chercher/creditProcess/achatProduitProcessus";
import Invoice from "app/modules/dashboard/invoice"



import Banks from "app/modules/dashboard/banks/banks";
import BanksRoutes from "app/modules/dashboard/banks";
import InvoiceRoutes from "app/modules/dashboard/invoice";
import AlgoCreditScore from "app/modules/dashboard/algoCreditScore/algoCreditScore";
import {AUTHORITIES} from "app/config/constants";
import PrivateRoute from "app/shared/auth/private-route";
const AdministrationRoutes = () => (
  <div>
    <ErrorBoundaryRoutes>
      <Route path="/*" element={<DashboardLayout />} >
      <Route path="member-user/*" element={
        <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}><MemberUserRoutes /></PrivateRoute>}  />
        <Route path="member-user-commercant/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.MERCHANT]}><MemberUserCommercantRoutes /></PrivateRoute>}  />
      <Route path="address/*" element={<Address />} />
      <Route path="agencies/*" element={<Agencies />} />

      <Route path="claim/*" element={
        <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
        <Claim />
        </PrivateRoute>} />
        <Route path="claim-user/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
            <ClaimUser />
          </PrivateRoute>} />
      <Route path="contact/*" element={
        <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}><Contact /></PrivateRoute>} />

        <Route path="credit_rapport/*" element={<PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}><CreditRapport /></PrivateRoute>} />

        <Route path="notification/*" element={<PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}><Notification/></PrivateRoute>} />
      <Route path="payment/*" element={
        <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN,AUTHORITIES.MERCHANT]}><Payment/></PrivateRoute>} />


      <Route path="product/*" element={<Product/>} />

        <Route path="/*" element={<Overview/>} />


        <Route path="rechercher/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.MERCHANT]}><Chercher /></PrivateRoute>} />


        <Route path="rechercher/AchatProduitProcessus/:id" element={<AchatProduitProcessus/>}/>
        <Route path="Customers/*" element={<MemberUserRoutes/>} />
        <Route path="bank/*" element={<BanksRoutes/>} />

        <Route path="invoice/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN,AUTHORITIES.MERCHANT]}><Invoice/></PrivateRoute>} />




        <Route path="algoCreditScore/*" element={<AlgoCreditScore />} />

      </Route>
    </ErrorBoundaryRoutes>
  </div>
);

export default AdministrationRoutes;
