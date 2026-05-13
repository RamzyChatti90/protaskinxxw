import dayjs from 'dayjs/esm';

import { ITask, NewTask } from './task.model';

export const sampleWithRequiredData: ITask = {
  id: 9181,
  title: 'pourvu que de peur que',
};

export const sampleWithPartialData: ITask = {
  id: 2746,
  title: "d'après peindre conférer",
  description: '../fake-data/blob/hipster.txt',
  dueDate: dayjs('2026-05-12'),
  createdAt: dayjs('2026-05-12T18:05'),
};

export const sampleWithFullData: ITask = {
  id: 13396,
  title: 'aussitôt que sitôt que',
  description: '../fake-data/blob/hipster.txt',
  priority: 'MEDIUM',
  status: 'DONE',
  dueDate: dayjs('2026-05-12'),
  createdAt: dayjs('2026-05-13T05:46'),
};

export const sampleWithNewData: NewTask = {
  title: 'dénoncer',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
