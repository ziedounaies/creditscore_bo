import dayjs from 'dayjs';
import { ICreditRapport } from 'app/shared/model/credit-rapport.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IAddress } from 'app/shared/model/address.model';
import { IPayment } from 'app/shared/model/payment.model';
import { IClaim } from 'app/shared/model/claim.model';
import { INotification } from 'app/shared/model/notification.model';
import { IContact } from 'app/shared/model/contact.model';
import { AcountType } from 'app/shared/model/enumerations/acount-type.model';
import { IdentifierType } from 'app/shared/model/enumerations/identifier-type.model';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IMemberUser {
  id?: number;
  userName?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  businessName?: string | null;
  birthDate?: dayjs.Dayjs | null;
  acountType?: keyof typeof AcountType | null;
  identifierType?: keyof typeof IdentifierType | null;
  identifierValue?: string | null;
  employersReported?: string | null;
  income?: string | null;
  expenses?: string | null;
  grossProfit?: string | null;
  netProfitMargin?: string | null;
  debtsObligations?: string | null;
  enabled?: boolean | null;
  role?: keyof typeof Role | null;
  createdAt?: dayjs.Dayjs | null;
  creditRapport?: ICreditRapport | null;
  invoice?: IInvoice | null;
  addresses?: IAddress[] | null;
  payments?: IPayment[] | null;
  claims?: IClaim[] | null;
  notifications?: INotification[] | null;
  contacts?: IContact[] | null;
}

export const defaultValue: Readonly<IMemberUser> = {
  enabled: false,
};
