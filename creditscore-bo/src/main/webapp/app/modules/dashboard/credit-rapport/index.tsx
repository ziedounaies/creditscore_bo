import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CreditRapport from './credit-rapport';
import CreditRapportDetail from './credit-rapport-detail';
import CreditRapportUpdate from './credit-rapport-update';
import CreditRapportDeleteDialog from './credit-rapport-delete-dialog';

const CreditRapportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CreditRapport />} />
    <Route path="new" element={<CreditRapportUpdate />} />
    <Route path=":id">
      <Route index element={<CreditRapportDetail />} />
      <Route path="edit" element={<CreditRapportUpdate />} />
      <Route path="delete" element={<CreditRapportDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CreditRapportRoutes;
