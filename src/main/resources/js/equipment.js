const App = {
    data() {
        return {
            sum: 0
        }
    }, 
    methods: {
        sum1() {
            var val_old = document.getElementById("id_sum").innerHTML
            var val_sum = document.getElementById("1").innerHTML
            var add_sum = parseInt(val_sum, 10) + parseInt(val_old, 10)
            document.getElementById("id_sum").innerHTML = add_sum
        },
        sum2() {
            var val_old = document.getElementById("id_sum").innerHTML
            var val_sum = document.getElementById("2").innerHTML
            var add_sum = parseInt(val_sum, 10) + parseInt(val_old, 10)
            document.getElementById("id_sum").innerHTML = add_sum
        },
        sum3() {
            var val_old = document.getElementById("id_sum").innerHTML
            var val_sum = document.getElementById("3").innerHTML
            var add_sum = parseInt(val_sum, 10) + parseInt(val_old, 10)
            document.getElementById("id_sum").innerHTML = add_sum
        },
        sum4() {
            var val_old = document.getElementById("id_sum").innerHTML
            var val_sum = document.getElementById("4").innerHTML
            var add_sum = parseInt(val_sum, 10) + parseInt(val_old, 10)
            document.getElementById("id_sum").innerHTML = add_sum
        },
        sum5() {
            var val_old = document.getElementById("id_sum").innerHTML
            var val_sum = document.getElementById("5").innerHTML
            var add_sum = parseInt(val_sum, 10) + parseInt(val_old, 10)
            document.getElementById("id_sum").innerHTML = add_sum
        },
        sum6() {
            var val_old = document.getElementById("id_sum").innerHTML
            var val_sum = document.getElementById("6").innerHTML
            var add_sum = parseInt(val_sum, 10) + parseInt(val_old, 10)
            document.getElementById("id_sum").innerHTML = add_sum

        },
        cleear() {
            document.getElementById("id_sum").innerHTML = 0
        }
    }
}

const app = Vue.createApp(App)

app.mount("#app")