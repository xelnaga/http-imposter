package net.xelnaga.httpimposter

import net.xelnaga.httpimposter.model.ImposterRequest
import net.xelnaga.httpimposter.model.ImposterResponse

class ImposterMapping {

    private Map<ImposterRequest, ImposterResponse> map

    ImposterMapping() {
        map = [:]
    }
    
    void put(ImposterRequest imposterRequest, ImposterResponse imposterResponse) {
        map[imposterRequest] = imposterResponse
    }

    ImposterResponse get(ImposterRequest imposterRequest) {
        return map[imposterRequest]
    }

    void clear() {
        map.clear()
    }
}
