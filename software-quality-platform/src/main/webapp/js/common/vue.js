
var app = new Vue({
    el: '#app',
    data: {
        message: 'Hello Team !'
    }
})

var app2 = new Vue({
    el: '#app-2',
    data: {
        message: 'You loaded this page on ' + new Date().toLocaleString()
    }
})

var app3 = new Vue({
    el: '#app-3',
    data: {
        seen: true
    }
})

new Vue({
    el: '#app4',
    data () {
        return {
            info: null
        }
    },
    mounted () {
        axios
            .get('http://localhost:8080/users')
            .then(response => (this.info = response.data))
    }
})
