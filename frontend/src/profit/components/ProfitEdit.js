import React from 'react';
import { connect } from 'react-redux';
import { fetchProfit, editProfit } from '../actions';
import ProfitForm from './ProfitForm';

class ProfitEdit extends React.Component {
  componentDidMount() {
    this.props.fetchProfit(this.props.match.params.id);
  }

  onSubmit = formValues => {
    this.props.editProfit(this.props.match.params.id, formValues);
  };

  render() {
    if (!this.props.profit) {
      return <div>Loading...</div>;
    }

    console.log(this.props.profit);
    
    return (
      <ProfitForm initialValues={this.props.profit} onSubmit={this.onSubmit} title="Edit Profit" />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { profit: state.profits[ownProps.match.params.id] };
};

export default connect(mapStateToProps, { fetchProfit, editProfit })(ProfitEdit);
