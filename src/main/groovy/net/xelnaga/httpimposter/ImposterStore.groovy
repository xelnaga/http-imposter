package net.xelnaga.httpimposter

class ImposterStore {

    private Map<ImposterRequest, ImposterResponse> map
    
    ImposterStore() {
        map = [:]
    }
    
    void put(ImposterRequest request, ImposterResponse response) {
        map.put(request, response)
    }

    ImposterResponse get(ImposterRequest request) {
        return map.get(request)
    }

    void clear() {
        map.clear()
    }
}
