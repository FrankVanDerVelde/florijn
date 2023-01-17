export default class InMemoryEntitiesService {

    _lastId;
    _entities;

    get entities() {
        return this._entities;
    }

    constructor(initialId = 10000, sampleCreator = null, numberOfSamples = 10) {
        this._lastId = initialId;
        this._entities = [];
        if (sampleCreator != null) {
            for (let i = 0; i < numberOfSamples; i++) {
                const sample = sampleCreator(this._lastId);
                this.save(sample);
                this._lastId++;
            }
        }

    }

    findAll() {
        return this._entities;
    }

    findById(id) {
        const foundEntity = this._entities.find(e => e?.id == id);
        return foundEntity;
    }

    save(entity) {
        if (entity == null) return;
      
        const index = this._entities.findIndex(e => e?.id == entity.id);
        if (index >= 0) {
            this._entities[index] = entity;
        } else {
            if (entity?.id == 0 || entity?.id == undefined) entity.id = ++this._lastId;
            this._entities.push(entity);
        }
        return entity;
    }

    deleteById(id) {
        this._entities = this._entities.filter(e => e?.id != id);
    }

}