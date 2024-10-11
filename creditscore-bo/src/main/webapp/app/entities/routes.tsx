import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MemberUser from './member-user';
import Address from './address';
import Contact from './contact';
import Claim from './claim';
import Product from './product';
import Payment from './payment';
import Invoice from './invoice';
import Agencies from './agencies';
import Banks from './banks';
import CreditRapport from './credit-rapport';
import Notification from './notification';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="member-user/*" element={<MemberUser />} />
        <Route path="address/*" element={<Address />} />
        <Route path="contact/*" element={<Contact />} />
        <Route path="claim/*" element={<Claim />} />
        <Route path="product/*" element={<Product />} />
        <Route path="payment/*" element={<Payment />} />
        <Route path="invoice/*" element={<Invoice />} />
        <Route path="agencies/*" element={<Agencies />} />
        <Route path="banks/*" element={<Banks />} />
        <Route path="credit-rapport/*" element={<CreditRapport />} />
        <Route path="notification/*" element={<Notification />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
