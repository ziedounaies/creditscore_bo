import React from 'react';
import AchatProduitProcessus from "app/modules/dashboard/chercher/creditProcess/achatProduitProcessus";
import { Route } from 'react-router-dom';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';




import DashboardLayout from "app/modules/dashboard/layout";
import Chercher from "app/modules/dashboard/chercher/chercher";

const RechercherRoutes = () => (
  <div>
    <ErrorBoundaryRoutes>
      <Route path="achatProduitProcessus/*" element={<AchatProduitProcessus/>}/>
    </ErrorBoundaryRoutes>
  </div>
);

export default RechercherRoutes;
