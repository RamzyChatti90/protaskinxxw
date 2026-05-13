import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 8109,
  name: 'au-dedans de conférer mal',
};

export const sampleWithPartialData: ICategory = {
  id: 7001,
  name: 'pin-pon surveiller diablement',
  color: '#f159c0',
  icon: 'fa-J',
};

export const sampleWithFullData: ICategory = {
  id: 28780,
  name: 'plouf',
  color: '#53bde1',
  icon: 'fa-l@Qxf',
};

export const sampleWithNewData: NewCategory = {
  name: 'aussitôt que cocorico quand ?',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
