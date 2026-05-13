import dayjs from 'dayjs/esm';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { Priority } from 'app/entities/enumerations/priority.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface ITask {
  id: number;
  title?: string | null;
  description?: string | null;
  priority?: keyof typeof Priority | null;
  status?: keyof typeof Status | null;
  dueDate?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs | null;
  owner?: Pick<IAppUser, 'id'> | null;
}

export type NewTask = Omit<ITask, 'id'> & { id: null };
