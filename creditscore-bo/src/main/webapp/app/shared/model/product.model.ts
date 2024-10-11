import dayjs from 'dayjs';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IPayment } from 'app/shared/model/payment.model';

export interface IProduct {
  id?: number;
  name?: string | null;
  serialNumber?: string | null;
  guarantee?: string | null;
  price?: string | null;
  createdAt?: dayjs.Dayjs | null;
  invoice?: IInvoice | null;
  payments?: IPayment[] | null;
}

export const defaultValue: Readonly<IProduct> = {};
