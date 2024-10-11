import dayjs from 'dayjs';
import { IMemberUser } from 'app/shared/model/member-user.model';
import { IProduct } from 'app/shared/model/product.model';
import { IPayment } from 'app/shared/model/payment.model';
import { ICreditRapport } from 'app/shared/model/credit-rapport.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';

export interface IInvoice {
  id?: number;
  invoiceNumber?: string | null;
  amount?: string | null;
  status?: keyof typeof StatusType | null;
  createdAt?: dayjs.Dayjs | null;
  memberUser?: IMemberUser | null;
  products?: IProduct[] | null;
  payments?: IPayment[] | null;
  creditRapport?: ICreditRapport | null;
}

export const defaultValue: Readonly<IInvoice> = {};
