import dayjs from 'dayjs/esm';

import { ISprint, NewSprint } from './sprint.model';

export const sampleWithRequiredData: ISprint = {
  id: 4944,
  name: 'autant insipide',
};

export const sampleWithPartialData: ISprint = {
  id: 21300,
  name: 'par suite de de façon à ce que étant donné que',
};

export const sampleWithFullData: ISprint = {
  id: 15912,
  name: 'sale à bas de envers',
  description: '../fake-data/blob/hipster.txt',
  startDate: dayjs('2026-05-13'),
  createdAt: dayjs('2026-05-13T05:18'),
};

export const sampleWithNewData: NewSprint = {
  name: 'dring',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
