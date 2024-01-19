let pizzaData = {
  days: [],
  budget: [],
  probability: [],
  customers: [],
}

// Проверяем, поддерживает ли браузер нужные API
if (window.File && window.FileReader && window.FileList && window.Blob) {
  document.getElementById('file').addEventListener('change', function(e) {
    var file = e.target.files[0];
    //var output = document.getElementById('output');
    var reader = new FileReader();
    reader.onload = function(e) {
        var text = e.target.result;
        var lines = text.split('\n');
        lines.pop();
        toPizzaData(lines);
    };
    
    // Начинаем чтение выбранного файла
    reader.readAsText(file);
  });
} else {
  alert('Нет возможности прочитать файл');
}

function toPizzaData(array) {
  array.forEach(element => {
    console.log(element)
    var e = element.split(' ');
    pizzaData.days.push(e[0]);
    pizzaData.budget.push(e[1]);
    pizzaData.probability.push(e[2]);
    pizzaData.customers.push(e[3]);
  });
  console.log(pizzaData);
  startCharting();
}

function startCharting(){
  
  const dataBudget = {
    labels: pizzaData.days,
    datasets: [{
      label: 'Бюджет пиццерии',
      backgroundColor: 'rgb(255, 99, 132)',
      borderColor: 'rgb(255, 99, 132)',
      data: pizzaData.budget,
    }]
  };
  const dataCustomers = {
    labels: pizzaData.days,
    datasets: [{
      label: 'Количество посетителей',
      backgroundColor: 'rgb(255, 99, 132)',
      borderColor: 'rgb(255, 99, 132)',
      data: pizzaData.customers,
    }]
  };
  const dataProbability = {
    labels: pizzaData.days,
    datasets: [{
      label: 'Вероятность посещения',
      backgroundColor: 'rgb(255, 99, 132)',
      borderColor: 'rgb(255, 99, 132)',
      data: pizzaData.probability,
    }]
  };
  
  const configBudget = {
    type: 'line',
    data: dataBudget,
    options: {}
  };
  const configCustomers = {
    type: 'line',
    data: dataCustomers,
    options: {}
  };
  const configProbability = {
    type: 'line',
    data: dataProbability,
    options: {}
  };

  const myChart = new Chart(
    document.getElementById('myChart'),
    configBudget
  );
  const myChart2 = new Chart(
    document.getElementById('myChart1'),
    configCustomers
  );
  const myChart3 = new Chart(
    document.getElementById('myChart2'),
    configProbability
  );
}